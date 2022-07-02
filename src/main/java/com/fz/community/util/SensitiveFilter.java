package com.fz.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zxf
 * @date 2022/6/18
 */
@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    //要替换的字符
    private static final String REPLACEMENT = "***";

    /**
     * 根节点
     */
    private TrieNode rootNode = new TrieNode();

    @PostConstruct
    public void init(){
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ){
            String keyword;
            while((keyword = reader.readLine())!= null){
                //添加到前缀树
                this.addKeyword(keyword);
            }
        }catch (IOException e){
            logger.error("加载文件敏感词失败："+e.getMessage());
        }

    }

    /**
     * 将一个敏感词添加到前缀树
     * @param keyword
     */
    private void addKeyword(String keyword){
        TrieNode tempNode = rootNode;
        for(int i = 0;i < keyword.length();i++){
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if(subNode == null){
                //初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }
            tempNode = subNode;

            //设置结束标识
            if(i == keyword.length()-1){
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤方法
     * @param text
     * @return
     */
    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;
        StringBuilder sb = new StringBuilder();

        while(position < text.length()){

            char c = text.charAt(position);
            //跳过符号
            if(isSymbol(c)){
                if(tempNode == rootNode){
                    sb.append(c);
                    begin++;
                }
                position++;
                continue;
            }
            //不是符号的话，检查下级节点
            tempNode = tempNode.getSubNode(c);
            if(tempNode == null){
                //以begin开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                position = ++begin;
                tempNode = rootNode;
            }else if(tempNode.isKeywordEnd() ){
                //发现敏感词，将begin到position字符串代替成*
                sb.append(REPLACEMENT);
                begin = ++position;
                tempNode = rootNode;
            }else{
                //继续检查下一个字符
                position++;
            }
        }
        //将最后一批字符计入结果
        sb.append(text.substring(begin));
        return sb.toString();
    }

    /**
     * 判断是否是符号
     * @param c
     * @return
     */
    private boolean isSymbol(Character c){
        //0x2E80到0x9FFF是东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    /**
     * 构造前缀树
     */
    private class TrieNode{

        private boolean isKeywordEnd = false;

        private Map<Character,TrieNode> subNodes = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }
        public void addSubNode(Character c, TrieNode node){
            subNodes.put(c,node);
        }
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }
}
