package com.minera.core.data.source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.minera.core.data.utils.FirebaseResponse
import com.minera.core.domain.constant.Collections
import com.minera.core.domain.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class AuthDataSource(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    fun signIn(email: String, password: String): Flow<FirebaseResponse<String>> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnFailureListener {
                trySend(FirebaseResponse.Error("Terjadi Kesalahan"))
            }
            .addOnSuccessListener {
                trySend(FirebaseResponse.Success("Berhasil masuk"))
            }

        awaitClose()
    }

    fun signUp(user: User, password: String): Flow<FirebaseResponse<String>> = callbackFlow {
        auth.createUserWithEmailAndPassword(user.email!!, password)
            .addOnFailureListener {
                trySend(FirebaseResponse.Error("Terjadi Kesalahan"))
                close(it)
            }
            .addOnSuccessListener {
                it.user?.uid?.let { userId ->
                    launch {
                        saveUserData(user.copy(id = userId)).collect { response ->
                            trySend(response)
                            auth.signOut()
                        }
                        close()
                    }
                } ?: {
                    trySend(FirebaseResponse.Error("Gagal mendapatkan User ID"))
                    close()
                }
            }

        awaitClose()
    }

    private fun saveUserData(user: User) : Flow<FirebaseResponse<String>> = callbackFlow {
        firestore.collection(Collections.USER_COLLECTION).document(user.id).set(user)
            .addOnFailureListener {
                trySend(FirebaseResponse.Error("Terjadi Kesalahan"))
                close()
            }
            .addOnSuccessListener {
                trySend(FirebaseResponse.Success("Berhasil mendaftar, silahkan login"))
                close()
            }

        awaitClose()
    }

    fun getRegistrationStatus(): Flow<FirebaseResponse<Int>> = callbackFlow {
        firestore.collection(Collections.USER_COLLECTION).document(auth.uid!!)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(FirebaseResponse.Error("Terjadi kesalahan pada server"))
                    close()
                    return@addSnapshotListener
                }

                value?.toObject(User::class.java)?.let {
                    trySend(FirebaseResponse.Success(it.registrationStatus.status))
                    close()
                }
            }

        awaitClose()
    }

    fun signOut() {
        auth.signOut()
    }

    fun isUserExist(): Flow<Boolean> = callbackFlow {
        if (auth.currentUser != null) {
            trySend(true)
            close()
        }
        else {
            trySend(false)
            close()
        }

        awaitClose()
    }
}