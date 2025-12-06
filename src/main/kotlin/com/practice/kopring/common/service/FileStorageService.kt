package com.practice.kopring.common.service

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@Service
class FileStorageService(
    @Value("\${file.dir}")
    private val uploadDir: String,
) {
    @PostConstruct
    fun init() {
        val directory = File(uploadDir)
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }

    fun storeFile(file: MultipartFile): String {
        // 1. 파일 검증
        if (file.isEmpty) {
            throw IllegalArgumentException("파일이 비어있습니다.")
        }

        val contentType = file.contentType
        if (contentType == null || !contentType.startsWith("image/")) {
            throw IllegalArgumentException("이미지 파일만 업로드 가능합니다.")
        }

        // 2. 파일명 생성
        val originalFilename = file.originalFilename ?: "unknown"
        val extension = originalFilename.substringAfterLast(".", "jpg")
        val timestamp = System.currentTimeMillis()
        val newFilename = "${UUID.randomUUID()}_$timestamp.$extension"

        // 3. 날짜별 폴더
        val dateFolder = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val targetDir = File(uploadDir, dateFolder)
        if (!targetDir.exists()) {
            targetDir.mkdirs()
        }

        // 4. 파일 저장
        val targetFile = File(targetDir, newFilename)
        file.transferTo(targetFile)

        // 5. URL 반환
        return "/images/$dateFolder/$newFilename"
    }

    fun deleteFiles(fileUrls: List<String>) {
        fileUrls.forEach { deleteFile(it) }
    }

    private fun deleteFile(fileUrl: String): Boolean {
        try {
            val filePath = fileUrl.replace("/images/", "")
            val file = File(uploadDir, filePath)
            return if (file.exists()) {
                file.delete()
            } else {
                false
            }
        } catch (e: Exception) {
            return false
        }
    }
}