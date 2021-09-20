cmake_minimum_required(VERSION 3.17)
project(process C)

set(CMAKE_C_STANDARD 11)

add_executable(${PROJECT_NAME} WIN32 main.c window.c process.c process.h window.h)