package com.modularwarfare.client.config;

public class BackpackRenderConfig {

    public String modelFileName = "";

    public Extra extra = new Extra();

    public static class Extra {
        /**
         * This models overall scale for rendering
         */
        public float modelScale = 1.0F;
    }
}