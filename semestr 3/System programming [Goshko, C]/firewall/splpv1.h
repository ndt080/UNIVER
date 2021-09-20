enum test_status {
    MESSAGE_INVALID,
    MESSAGE_VALID
};

enum Direction {
    A_TO_B,
    B_TO_A
};

struct Message {
    enum Direction	direction;
    char			*text_message;
};

extern enum test_status validate_message( struct Message* pMessage );