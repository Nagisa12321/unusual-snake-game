#include <string.h>
#include <fcntl.h>

#include "snake.h"
int main() {
    // connect the server
    int sock;
    if ((sock = connect_server()) == -1) {
        printf("can't not find the server.\n");
        exit(-1);
    }
    // input buf
    char buf[100] = { 0 };

    // print the logo XD
    int fd;
    fd = open("logo.txt", O_RDONLY);
    while (read(fd, buf, 1)) {
        printf("%s", buf);
        sleep(0.1);
    }
    close(fd);
    while (1) {
        // chose the player
        printf("what player do you want to play? (snake, food or exit)\n > ");
        int playerType;
        scanf("%s", buf);
        if (strcmp(buf, "exit") == 0) {
            break;
        } else if (strcmp(buf, "snake") == 0) {
            playerType = 0;
            send_mess(make_mess(0, 0, ""), sock);
        } else if (strcmp(buf, "food") == 0) {
            playerType = 1;
            send_mess(make_mess(0, 1, ""), sock);
        } else {
            printf("[client mess]: please input snake, food or exit. XD\n");
            continue;
        }

        // you have chose a toy here
        printf("... ... ... Now waiting for the other player ... ... ... \n");
        struct message* mess = recv_mess(sock);
        if (mess->type == 3) {
            while (1) {
                printf("the other player has been found, now input [prepare] to get prepare\n");
                printf("only two players are all prepared, the game will start! XD\n > ");
                scanf("%s", buf);
                if (strcmp(buf, "prepared") == 0) {
                    printf("pt = %d\n", playerType);
                    send_mess(make_mess(2, playerType, ""), sock);
                    printf("... ... ... Now waiting for the other player to get prepared ... ... ... \n");
                    free_mess(mess);
                    mess = recv_mess(sock);
                    if (mess->type == 0) {
                        // start the game

                        int pid;
                        printf("pid == %d\n", pid);
                        // start the process to listen the map from server
                        if (!(pid = fork())) {
                            struct message* m;
                            while (1) {
                                // 父进程和子进程共享相同的文件描述符
                                m = recv_mess(sock);
                                // map notify
                                if (m->type == 1) {
                                    int len_i = m->mess[0];
                                    int len_j = m->mess[1];
                                    int idx = 2;
                                    system("clear");
                                    for (int i = 0; i < len_i; i++) {
                                        for (int j = 0; j < len_j; j++) {
                                            if (m->mess[idx] == 0) 
                                                printf("  ");
                                            // else if (m->mess[idx] == 1) 
                                            //     printf("■ ");
                                            else if (m->mess[idx] == 4)
                                                printf("■ ");
                                            else 
                                                printf("%c ", rand() % 94 + 33);
                                            idx++;
                                        }
                                        printf("\n");
                                        fflush(stdout);
                                    }
                                    printf("\n");
                                    fflush(stdout);
                                }

                                char buf2[2] = { 0 };
                                // game over
                                if (m->type == 2) {
                                    system("clear");
                                    if (m->playerType == 0) 
                                        fd = open("snakewon.txt", O_RDONLY);
                                    else fd = open("foodwon.txt", O_RDONLY);

                                    while (read(fd, buf2, 1)) {
                                        printf("%s", buf2);
                                        sleep(0.1);
                                    }
                                    close(fd);
                                    close(sock);
                                    free_mess(m);
                                    exit(0);
                                }

                                free_mess(m);
                            }
                        } else {

                            printf("pid == %d\n", pid);
                            // listen the keyboard
                            init_keyboard_listener();
                            char in;
                            while (1) {
                                in = get_keyboard_char();
                                if (in == ESC) {
                                    close_keyboard_listener();
                                    close(sock);
                                    exit(0);
                                }
                                if (in == LEFT) 
                                    send_mess(make_mess(1, playerType, "2"), sock);
                                if (in == RIGHT) 
                                    send_mess(make_mess(1, playerType, "3"), sock);
                                if (in == UP) 
                                    send_mess(make_mess(1, playerType, "0"), sock);
                                if (in == DOWN) 
                                    send_mess(make_mess(1, playerType, "1"), sock);
                            }    
                        }
                    }
                } else {
                    printf("[client mess]: wtf you input???\n");
                }
            }
        } else if (mess->type == 4) {
            continue;
        }
    }
} 