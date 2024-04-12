package com.example.habittracker.Structs;

public class PayloadOption {
    private Object payload;
    private CachedString option;
    public PayloadOption(CachedString option, Object payload){
        this.payload = payload;
        this.option = option;
    }

    public Object getPayload() {
        return payload;
    }

    public String getString() {
        return option.getString();
    }

    public CachedString getCachedString(){
        return option;
    }
    @Override
    public boolean equals(Object object){
        if(object instanceof PayloadOption) {
            PayloadOption payloadOption = (PayloadOption) object;
            if(object.equals(payloadOption.option))
                return true;
        }
        return false;
    }
}