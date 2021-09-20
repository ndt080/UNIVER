cmake_minimum_required(VERSION 3.17)
project(openMP_1)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -fopenmp")
add_executable(openMP_1 main.cpp)