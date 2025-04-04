package com.frontend.oportunia.data.datasource

class UserDataSourceImpl()
//    private val userMapper: UserMapper,
//) : UserDataSource {
//
//    override suspend fun getUsers(): Flow<List<UserDto>> = flow {
//        val users = UserProvider.findAllUsers()  // Simula la obtención de usuarios
//        emit(users.map { userMapper.mapToDto(it) })
//    }
//
//    override suspend fun getUserById(id: Long): UserDto? = UserProvider.findUserById(id)?.let {
//        userMapper.mapToDto(it)
//    }
//
//    override suspend fun insertUser(userDto: UserDto) {
//        // Implementación de mock, no se necesita persistencia en este ejemplo
//    }
//
//    override suspend fun updateUser(userDto: UserDto) {
//        // Implementación de mock, no se necesita persistencia en este ejemplo
//    }
//
//    override suspend fun deleteUser(userDto: UserDto) {
//        // Implementación de mock, no se necesita persistencia en este ejemplo
//    }
//}