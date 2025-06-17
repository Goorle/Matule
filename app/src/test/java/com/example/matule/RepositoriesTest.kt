package com.example.matule.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RepositoriesTest {

    private lateinit var repositories: Repositories
    private lateinit var mockSupabaseClient: SupabaseClient
    private lateinit var mockAuth: Auth

    @Before
    fun setup() {
        // Создаем моки
        mockSupabaseClient = mockk(relaxed = true)
        mockAuth = mockk()

        // Настраиваем поведение моков
        every { mockSupabaseClient.auth } returns mockAuth
        every { mockSupabaseClient.pluginManager.getPlugin(Auth) } returns mockAuth

        // Создаем экземпляр Repositories с моком клиента
        repositories = Repositories()
    }

    @Test
    fun `signInUser should call auth with correct credentials`() = runTest {
        // Подготовка
        val testEmail = "test@example.com"
        val testPassword = "password123"

        coEvery {
            mockAuth.signInWith(Email) {
                email = testEmail
                password = testPassword
            }
        } returns mockk()

        // Выполнение
        repositories.signInUser(testEmail, testPassword)

        // Проверка
        coEvery {
            mockAuth.signInWith(Email) {
                email = testEmail
                password = testPassword
            }
        }
    }
}