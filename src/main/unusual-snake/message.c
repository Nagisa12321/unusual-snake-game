#include "snake.h"

#include <stdlib.h>
int connect_server() {
    // connect the server
    int sock = socket(AF_INET, SOCK_STREAM, 0);
    // 向服务器（特定的IP和端口）发起请求
    struct sockaddr_in serv_addr;
    memset(&serv_addr, 0, sizeof(serv_addr));  // 每个字节都用0填充
    serv_addr.sin_family = AF_INET;  // 使用IPv4地址
    serv_addr.sin_addr.s_addr = inet_addr(IP);  // 具体的IP地址
    serv_addr.sin_port = htons(PORT);  //端口
    if (connect(sock, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) == -1) {
        close(sock);
        return -1;
    }

    return sock;
}

struct message* make_mess(int type, int playerType, char* message) {
    struct message* mess = (struct message *) malloc(sizeof(struct message));
    mess->type = type;
    mess->playerType = playerType;
    mess->mess = message;
}

void free_mess(struct message* mess) {
    free(mess);
    mess = NULL;
}

void send_mess(struct message* mess, int sock) {
    char buf[100] = { 0 };
    buf[0] = mess->type;
    buf[1] = mess->playerType;
    int idx = 0;
    while (mess->mess[idx]) {
        buf[idx + 2] = mess->mess[idx];
        idx++;
    }
    write(sock, buf, idx + 2);
    write(sock, "#", 1);

    free_mess(mess);
}

struct message* recv_mess(int sock) {
    char* mess = (char *) malloc(sizeof(char) * 4096);
    memset(mess, 0, sizeof(char) * 4096);
    char buf[1] = { 0 }; 
    int idx = 0; 
    read(sock, buf, 1);
    int type = buf[0];
    read(sock, buf, 1);
    int playerType = buf[0];

    read(sock, buf, 1);
    while (buf[0] != '#') {
        mess[idx++] = buf[0];
        read(sock, buf, 1);
    }

    
    printf("[server reply]: %s\n", mess);
    return make_mess(type, playerType, mess);
}

void show_mess(struct message* mess) {
    printf("Message[type=%d, playerType=%d, context='%s']\n", 
            mess->type, mess->playerType, mess->mess);
}
