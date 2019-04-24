package com.gemalto.tokenlibrary.utilities;

public enum ProgressState {
    SUCCESS_DEVICE_RESPONSE(0),
    SEND_TOKEN_REQ(1),
    RECEIVE_TOKEN(2),
    SEND_DEVICE_TOKEN(3),
    RECEIVE_CHALLENGE(4),
    SEND_CHALLENGE_RESPONSE(5),
    UNLOCK_DOOR_PACKET(501),
    LOCK_DOOR_PACKET(502),
    LED_ON(503),
    LED_OFF(504),
    UNKNOWN_PACKET(-100),
    SEND_TOKEN_REQ_FAILED(-101),
    RECEIVE_TOKEN_FAILED(-102),
    RECEIVE_CHALLENGE_FAILED(-103),
    SEND_CHALLENGE_RESPONSE_FAILED(-104),
    NUMBER_OF_CONNECTION_EXCEEDED(-105),
    CHALLENGE_RESPONSE_FAILED (-106),
    TOKEN_AUTH_FAILED(-107),
    TOKEN_PARSING_FAILED(-108);

    private int value;

    private ProgressState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

