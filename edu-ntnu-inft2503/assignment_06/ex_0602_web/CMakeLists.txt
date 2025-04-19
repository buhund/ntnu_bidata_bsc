cmake_minimum_required(VERSION 3.29)
project(exercise_0602_web)

set(CMAKE_CXX_STANDARD 20)

add_executable(exercise_0602_web main.cpp)

find_package(Boost REQUIRED COMPONENTS system)
include_directories(${Boost_INCLUDE_DIRS})
target_link_libraries(exercise_0602_web ${Boost_LIBRARIES})
