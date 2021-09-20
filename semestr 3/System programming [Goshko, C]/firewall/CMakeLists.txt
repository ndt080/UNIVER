cmake_minimum_required(VERSION 3.17)
project(firewall C)

set(CMAKE_C_STANDARD 11)

add_executable(firewall main.c splpv1.h splpv1.c)