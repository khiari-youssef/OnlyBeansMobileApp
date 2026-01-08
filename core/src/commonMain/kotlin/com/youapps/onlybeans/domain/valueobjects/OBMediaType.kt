package com.youapps.onlybeans.domain.valueobjects


data class OBFile(
    val uri : String,
    val isLocal : Boolean = (uri.startsWith("https://") or uri.startsWith("http://")).not(),
    val format : String?=null,
) {

    init {

    }
    fun toOBFileType() : OBFileType? = when(format){
        "image/jpeg","image/jpg","image/png","image/svg","image/webp" -> OBFileType.Image
        "video/mp4" -> OBFileType.Video
         "application/pdf" -> OBFileType.PDF
         "audio/mpeg" -> OBFileType.Audio
         "application/gpx+xml" -> OBFileType.GPX
         "application/vnd.google-earth.kml+xml"  -> OBFileType.KML
         "application/geo+json" -> OBFileType.GeoJson
         "application/json" -> OBFileType.Json
         "text/plain" -> OBFileType.Text
         else -> OBFileType.Unknown
    }

    override fun equals(other: Any?): Boolean =
        (other is OBFile) &&
        (other.uri == this.uri) &&
        other.isLocal == this.isLocal &&
        other.format == this.format

    override fun toString(): String = buildString {
        if(uri.isNotBlank()) {
            append("uri:${uri}??")
        }
        append("isLocal:${isLocal}??")
        if (format != null) {
            append("format:${format}")
        }
    }

    companion object{

         fun fromString(data : String) : OBFile? = runCatching {
                 data.split("??").let { list->
                     OBFile(
                         uri = list[0].removePrefix("uri:"),
                         isLocal = list[1].removePrefix("isLocal:").toBoolean(),
                         format = list[2].removePrefix("format:")
                     )
                 }
         }.getOrNull()

    }

    override fun hashCode(): Int {
        var result = isLocal?.hashCode() ?: 0
        result = 31 * result + uri.hashCode()
        result = 31 * result + (format?.hashCode() ?: 0)
        return result
    }
}

enum class  OBFileType {
Image,Video,PDF,Audio,GPX,KML,GeoJson,Json,Text,Unknown
}
