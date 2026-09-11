cmake_minimum_required(VERSION 3.20)
project(c C)

set(CMAKE_C_STANDARD 11)

# Define a pasta bin/ para armazenar todos os executáveis gerados
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/bin")

# Suporte à biblioteca de threads (pthread / C11 threads)
find_package(Threads REQUIRED)

# Cria executáveis independentes para cada arquivo .c em src/ (ignorando arquivos vazios)
file(GLOB SOURCES "src/*.c")
foreach(SOURCE_FILE ${SOURCES})
    file(SIZE ${SOURCE_FILE} FILE_SIZE)
    if(FILE_SIZE GREATER 0)
        get_filename_component(TARGET_NAME ${SOURCE_FILE} NAME_WE)
        add_executable(${TARGET_NAME} ${SOURCE_FILE}
                src/intro_pthreads.c)
        target_link_libraries(${TARGET_NAME} PRIVATE Threads::Threads)
    endif()
endforeach()

