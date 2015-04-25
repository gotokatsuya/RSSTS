package com.goka.rssts;

import com.goka.rssts.request.SlackClient;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class Rssts {

    // Call on application class

    public static void initialize(boolean debug, String slackToken, String slackChannels) {
        Config.DEBUG = debug;

        Config.TOKEN = slackToken;
        Config.CHANNELS = slackChannels;

        SlackClient.initialize();
    }

    // Release
    public static void initialize(String slackToken, String slackChannels) {
        initialize(false, slackToken, slackChannels);
    }
}
