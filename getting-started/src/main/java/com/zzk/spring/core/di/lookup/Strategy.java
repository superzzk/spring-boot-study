package com.zzk.spring.core.di.lookup;
public abstract class Strategy {
    public abstract String doExecute();
    public String execute() {
        return doExecute();
    }

    public static class StrategyA extends Strategy {
        @Override
        public String doExecute() {
            return "Strategy A";
        }
    }
    public static class StrategyB extends Strategy {
        @Override
        public String doExecute() {
            return "Strategy B";
        }
    }

}
