package com.goka.rssts;

import com.goka.rssts.request.SlackClient;

/**
 * Created by katsuyagoto on 2015/04/26.
 */
public class Rssts {

    /**
     *
     * Initialize method. Call on application class.
     *
     * @param enableLog
     * @param slackToken
     * @param slackChannels  //Comma separated list of channels to share the file into
     */
    public static void initialize(boolean enableLog, String slackToken, String slackChannels) {
        Config.ENABLE_LOG = enableLog;

        Config.SLACK_TOKEN = slackToken;
        Config.SLACK_CHANNELS = slackChannels;

        SlackClient.initialize();
    }

    // ENABLE LOG MODE for debug
    public static void initialize(String slackToken, String slackChannels) {
        initialize(true, slackToken, slackChannels);
    }


    public static void setSlackToken(String slackToken) {
        Config.SLACK_TOKEN = slackToken;
    }

    public static void setSlackChannels(String slackChannels) {
        Config.SLACK_CHANNELS = slackChannels;
    }

}
