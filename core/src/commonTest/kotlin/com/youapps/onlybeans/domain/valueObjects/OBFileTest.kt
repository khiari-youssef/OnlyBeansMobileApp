package com.youapps.onlybeans.domain.valueObjects

import com.youapps.onlybeans.domain.valueobjects.OBFile
import kotlin.test.Test


class OBFileTest {

val encodedFile : String = ""

val file1 : OBFile = OBFile(
    uri = "https://www.example.com/data",
    isLocal = false,
    format = null
)
val file2 : OBFile = OBFile(
    uri = "file://com.youapps.onlybeans/data/cache/image.png",
    isLocal = true,
    format = "image/png"
)




@Test
fun `When OBFile object is encoded with all attributes the correct pattern must be match`(): Unit {
    file2.toString() == "uri:file://com.youapps.onlybeans/data/cache/image.png??isLocal:true??format=image/png"
}

@Test
fun `When OBFile object is encoded with no file format the correct pattern must be match`(): Unit {
    file1.toString() == "uri:https://www.example.com/data??isLocal:false"
}

@Test
fun `When OBFile string is decoded then it must match the expect object`(): Unit {
    OBFile.fromString("uri:file://com.youapps.onlybeans/data/cache/image.png??isLocal:true??format=image/png") == file2
}





}