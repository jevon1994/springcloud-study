package cn.leon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class service {

    @Autowired
    private TestSend send;

    public void send(String message){
        send.send(message);
    }
}
