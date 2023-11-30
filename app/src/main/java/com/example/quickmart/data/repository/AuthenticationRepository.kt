package com.example.quickmart.data.repository

import com.example.quickmart.data.db.QuickMartDao
import com.example.quickmart.data.db.entities.UserEntity
import com.example.quickmart.models.User
import com.example.quickmart.utils.appUser
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object AuthenticationRepository {
    private val firebaseAuth = Firebase.auth
    private val firebaseFireStore = Firebase.firestore
    suspend fun isLoggedIn(dao: QuickMartDao): Boolean {
        val entity = dao.getUser()
        if (entity != null) {
            val user = User(entity.id, entity.firstName, entity.lastName, entity.email)
            appUser = user
            return true
        }
        return false
    }

    suspend fun createUserByEmailAndPassword(
        email: String,
        password: String
    ): User =
        suspendCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = firebaseAuth.currentUser
                        if (firebaseUser != null) continuation.resume(
                            User(
                                firebaseUser.uid,
                                email = firebaseUser.email
                            )
                        )
                        else continuation.resumeWithException(NullPointerException())
                    } else {
                        continuation.resumeWithException(
                            task.exception ?: Exception("Unknown error")
                        )
                    }
                }
        }

    suspend fun addUserToFireStore(user: User) =
        suspendCoroutine { continuation ->
            val usersCollection = firebaseFireStore.collection("users")
            val userDocument = usersCollection.document(user.id!!)
            userDocument.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (!documentSnapshot.exists()) {
                        userDocument.set(user)
                            .addOnSuccessListener {
                                continuation.resume(user)
                            }
                            .addOnFailureListener { task ->
                                continuation.resumeWithException(task)
                            }
                    }
                }
                .addOnFailureListener { task ->
                    continuation.resumeWithException(task)
                }
        }

    suspend fun loginUserByEmailAndPassword(
        email: String,
        password: String,
    ): String? =
        suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    continuation.resume(result.user!!.uid)
                }
                .addOnFailureListener { task ->
                    continuation.resumeWithException(task)
                }
        }

    suspend fun getUserDataFromServer(id: String): User =
        suspendCoroutine { continuation ->
            val usersCollection = firebaseFireStore.collection("users")
            val userDocument = usersCollection.document(id)
            userDocument.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (!documentSnapshot.exists()) {
                        continuation.resumeWithException(NullPointerException())
                    }
                    val appUserRemoteModel =
                        documentSnapshot.toObject(User::class.java)
                    continuation.resume(appUserRemoteModel!!)
                }
                .addOnFailureListener { task ->
                    continuation.resumeWithException(task)
                }
        }

    suspend fun writeUserDataToRoomDb(dao: QuickMartDao, userEntity: UserEntity) {
        dao.addUser(userEntity)
    }

    suspend fun logout(dao: QuickMartDao) {
        dao.deleteUser()
        firebaseAuth.signOut()
    }
}