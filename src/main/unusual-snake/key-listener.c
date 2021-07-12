#include "snake.h"

static struct termios *new_settings;
static struct termios *stored_settings;

void init_keyboard_listener() {
    // struct termios *new_settings = (struct termios *) malloc(sizeof(struct termios));
    // struct termios *stored_settings = (struct termios *) malloc(sizeof(struct termios));
    // tcgetattr(0, stored_settings);
    // *new_settings = *stored_settings;
    // new_settings->c_lflag &= (~ICANON);
    // new_settings->c_cc[VTIME] = 0;
    // tcgetattr(0, stored_settings);
    // new_settings->c_cc[VMIN] = 1;
}

char get_keyboard_char() {
    // tcsetattr(0,TCSANOW,new_settings);
    // char in = getchar();    
    // tcsetattr(0,TCSANOW,stored_settings);
    // return in;

    int input;
    char in;
        struct termios new_settings;
        struct termios stored_settings;
        tcgetattr(0, &stored_settings);
        new_settings = stored_settings;
        new_settings.c_lflag &= (~ICANON);
        new_settings.c_cc[VTIME] = 0;
        tcgetattr(0, &stored_settings);
        new_settings.c_cc[VMIN] = 1;
        tcsetattr(0, TCSANOW, &new_settings);
        
        in = getchar();
        tcsetattr(0, TCSANOW, &stored_settings);

        return in;
}

void close_keyboard_listener() {
    // free(new_settings);
    // free(stored_settings);
}