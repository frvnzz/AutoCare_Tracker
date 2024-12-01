package at.ac.fhstp.contactsapp.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ContactRemoteService {
    @GET("contacts")
    suspend fun findAllContacts(): List<ContactDto>

    @GET("contacts/{contactId}")
    suspend fun findContactById(@Path("contactId") id: Int): ContactDto

    @POST("contacts")
    suspend fun sendContact(@Body contactDto: ContactDto): ContactDto

}