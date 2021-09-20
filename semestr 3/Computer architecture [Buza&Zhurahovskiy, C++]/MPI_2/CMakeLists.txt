cmake_minimum_required(VERSION 3.17)
project(MPI_2)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED TRUE)

find_package(MPI) #make it REQUIRED, if you want
include_directories(SYSTEM ${MPI_INCLUDE_PATH})

add_executable(MPI_2 main.cpp Process.cpp Process.h)
target_link_libraries(MPI_2 ${MPI_C_LIBRARIES})