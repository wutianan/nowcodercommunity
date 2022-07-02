//package com.fz.newcoder.community.event;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fz.newcoder.community.entity.Event;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
///**
// * @author zxf
// * @date 2022/6/30
// */
//@Component
//public class EventProducer {
//
//    @Autowired
//    private KafkaTemplate kafkaTemplate;
//
//
//    public void fireEvent(Event event){
//        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
//    }
//}
