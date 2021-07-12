#include <stdio.h>
#include <termio.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>

#define END_CHAR    "#"
#define IP          "192.168.1.111"
#define PORT        8088

#define LEFT        97
#define RIGHT       100
#define UP          119
#define DOWN        115
#define ESC         27

struct message {
    int type;
    int playerType;
    char* mess;
};

int connect_server();

struct message* make_mess(int type, int playerType, char* mess);

void free_mess(struct message* mess);

void send_mess(struct message* mess, int sock);

struct message* recv_mess(int sock);

void show_mess(struct message* mess);

void init_keyboard_listener();

char get_keyboard_char();

void close_keyboard_listener();