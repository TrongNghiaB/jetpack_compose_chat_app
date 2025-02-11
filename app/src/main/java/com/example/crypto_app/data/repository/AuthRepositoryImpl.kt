package com.example.crypto_app.data.repository

import com.example.crypto_app.domain.model.profile.User
import com.example.crypto_app.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl() : AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val usersFireStore = FirebaseFirestore.getInstance().collection("users")

    override suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(email: String, password: String): Result<Unit> {
        return try {
            val res = auth.createUserWithEmailAndPassword(email, password).await()

            val newUser= User(
                id = res.user!!.uid,
                email = email,
                name = "username",
                image = "https://news.khangz.com/wp-content/uploads/2024/08/game-pikachu-co-dien.jpg",
            )

            addUserToFireStore(newUser)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addUserToFireStore(user: User): Result<Unit> {
        return try {
           usersFireStore
                .document(user.id)
                .set(user)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            println("Error adding user to firestore: ${e.message}")
            Result.failure(e)
        }
    }

    override fun isSignIn(): Boolean = auth.currentUser != null

    override fun signOut() {
        auth.signOut()
    }

    override fun userId(): String? = auth.currentUser?.uid

    override fun userEmail(): String? =  auth.currentUser?.email
}

