package org.example;

public class ProcessCon {
    Api apiClass;

    public ProcessCon(Api apiClass) {
        this.apiClass = apiClass;
    }

    public String process(Double param1, Double param2, String param3) {
        return apiClass.connection(param1, param2, param3).toUpperCase();
    }

}
