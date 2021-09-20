cmake_minimum_required(VERSION 3.17)
project(MPI_path1)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED TRUE)

find_package(MPI) #make it REQUIRED, if you want
include_directories(SYSTEM ${MPI_INCLUDE_PATH})

add_executable(MPI_path1 main.cpp Process.cpp Process.h)
target_link_libraries(MPI_path1 ${MPI_C_LIBRARIES})