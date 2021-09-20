cmake_minimum_required(VERSION 3.17)
project(openMP_2)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++20 -fopenmp")
add_executable(openMP_2 main.cpp)