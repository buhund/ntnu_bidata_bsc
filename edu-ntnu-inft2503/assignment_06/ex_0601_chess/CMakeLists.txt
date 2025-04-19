cmake_minimum_required(VERSION 3.29)
project(exercise_0601_chess)

set(CMAKE_CXX_STANDARD 20)

add_executable(exercise_0601_chess main.cpp
        ChessBoardPrint.cpp
        ChessBoardPrint.h
        ChessBoard.cpp
        ChessBoard.h)
