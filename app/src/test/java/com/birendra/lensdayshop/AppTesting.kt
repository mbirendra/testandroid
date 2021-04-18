package com.birendra.lensdayshop

import com.birendra.lensdayshop.api.RetrofitService
import com.birendra.lensdayshop.entity.BookingLensdays
import com.birendra.lensdayshop.entity.Lensdays
import com.birendra.lensdayshop.entity.User
import com.birendra.lensdayshop.repository.BookingRepository
import com.birendra.lensdayshop.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class AppTesting {
    @Test
    fun loginTesting() {
        runBlocking {
            val expectedResult = true
            val repository = UserRepository()
            var response = repository.loginUser("admins", "admin")
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun registrationTesting() {
        runBlocking {
            val expectedResult = true
            val repository = UserRepository()
            var user = User(
                fname = "Ashish",
                lname = "Pandey",
                username = "ppashish",
                email = "pashish@gmail.com",
                password = "12345",
                phone_number = "9843809633",
                address = "Gongabu"
            )
            var response = repository.registerUser(user)
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun addtocartTesting() {
        runBlocking {
            val expectedResult = true
            val repo = UserRepository()
            RetrofitService.token = "Bearer " + repo.loginUser("admins", "admin").token
            var repository = BookingRepository()
            var booking = BookingLensdays(
                product_id = Lensdays(_id = "606d4df33148064140d23993"),
                quantity = 1,
                delivery_address = "Jhapa",
                delivery_number = "112"
            )
            var response = repository.bookFurniture(booking)
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)

        }
    }

    @Test
    fun cartTesting() {
        runBlocking {
            val expectedResult = true
            val repo = UserRepository()
            RetrofitService.token = "Bearer " + repo.loginUser("admins", "admin").token
            var repository = BookingRepository()
            var response=repository.retrieveBooking()
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }
    @Test
    fun deleteTesting(){
        runBlocking {
            var expectedResult=true
            val repo = UserRepository()
            RetrofitService.token="Bearer " + repo.loginUser("admins","admin").token
            var repository=BookingRepository()
            var response=repository.deleteBooking("606741c9df90fd4010cfad2e")
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }
    @Test
    fun loginTestings() {
        runBlocking {
            val expectedResult = false
            val repository = UserRepository()
            var response = repository.loginUser("admins", "admin")
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun registrationTestings() {
        runBlocking {
            val expectedResult = true
            val repository = UserRepository()
            var user = User(
                fname = "Ashish",
                lname = "Pandey",
                username = "ppashish",
                email = "pashish@gmail.com",
                password = "12345",
                phone_number = "9843809633",
                address = "Gongabu"
            )
            var response = repository.registerUser(user)
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun addtocartTestings() {
        runBlocking {
            val expectedResult = true
            val repo = UserRepository()
            RetrofitService.token = "Bearer " + repo.loginUser("admins", "admin").token
            var repository = BookingRepository()
            var booking = BookingLensdays(
                product_id = Lensdays(_id = "606d4df33148064140d23993"),
                quantity = 1,
                delivery_address = "Jhapa",
                delivery_number = "112"
            )
            var response = repository.bookFurniture(booking)
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)

        }
    }

    @Test
    fun cartTestings() {
        runBlocking {
            val expectedResult = true
            val repo = UserRepository()
            RetrofitService.token = "Bearer " + repo.loginUser("admins", "admin").token
            var repository = BookingRepository()
            var response=repository.retrieveBooking()
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }
    @Test
    fun deleteTestings(){
        runBlocking {
            var expectedResult=false
            val repo = UserRepository()
            RetrofitService.token="Bearer " + repo.loginUser("admins","admin").token
            var repository=BookingRepository()
            var response=repository.deleteBooking("606741c9df90fd4010cfad2e")
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }
}