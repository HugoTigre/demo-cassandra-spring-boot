package com.pakybytes.demo.springboot.cassandra.delivery.viewmodels

interface BookVM

data class CreateBookVM(
        val title: String,
        val publisher: String,
        val tags: Set<String>
) : BookVM


data class UpdateBookVM(
        val id: String,
        val publisher: String
) : BookVM


data class DeleteBookVM(
        val id: String
) : BookVM