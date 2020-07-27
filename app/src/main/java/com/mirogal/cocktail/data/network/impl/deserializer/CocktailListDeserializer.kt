//package com.mirogal.cocktail.data.network.impl.deserializer
//
//class CocktailListDeserializer : JsonDeserializer<List<CocktailNetModel>> {
//
////    @Throws(JsonParseException::class)
////    override fun deserialize(
////        json: JsonElement?, typeOfT: Type,
////        context: JsonDeserializationContext
////    ): List<CocktailNetModel> {
////
////        return json!!.asJsonObject!!.let { jsonObject ->
////            val drinksJsonArray = jsonObject.get("drinks").asJsonArray
////            val list = mutableListOf<CocktailNetModel>()
////            drinksJsonArray.forEach {
////                val drinkJsonObject = it.asJsonObject
////
////                CocktailNetModel(
////                    id = drinkJsonObject.get("id").asLong,
////                    names = LocalizedStringNetModel(
////                        drinkJsonObject.getMemberStringOrEmpty("strDrink")
////                    )
////                )
////                //
////                //list.add(context.deserialize(it, deserializeType<CocktailNetModel>()))
////            }
////            list
////        }
////    }
//
//    //EXAMPLE
////    @Throws(JsonParseException::class)
////    override fun deserialize(json: JsonElement?, type: Type, context: JsonDeserializationContext): ClinicNetModel {
////        return json!!.asJsonObject!!.let { jsonObject ->
////            val checkMetroKey = if (jsonObject.has("metro_id")) "metro_id" else "metro_ids"
////            val metroIds = jsonObject.getIfHasMember(checkMetroKey).let { element ->
////                when {
////                    element == null -> emptyList()
////                    element.isJsonArray -> element.asJsonArray.map { if (it.isJsonPrimitive) it.asLong else -1L }
////                        .filter { it > -1L }
////                    element.isJsonPrimitive -> listOf(element.asLong)
////                    else -> emptyList()
////                }
////            }
////            val checkMetroStationsKey = if (jsonObject.has("metro_station")) "metro_station" else "metro_stations"
////            val metroStations = jsonObject.getIfHasMember(checkMetroStationsKey).let { element ->
////                when {
////                    element == null -> emptyList()
////                    element.isJsonArray -> element.asJsonArray.map { it.asStringOrEmpty() }.filter { it.isNotEmpty() }
////                    else -> listOf(element.asStringOrEmpty()).filter { it.isNotEmpty() }
////                }
////            }
////
////            val id = jsonObject.get("id").asLong
////            ClinicNetModel(
////                id = id,
////                cityId = jsonObject.get("city_id").asLong,
////                name = jsonObject.getMemberStringOrEmpty("name"),
////                image = jsonObject.getMemberStringOrEmpty("image"),
////                address = jsonObject.getMemberStringOrEmpty("address"),
////                districtId = jsonObject.getIfHasMember("district_id")?.asLong ?: -1L,
////                neighbourhood = jsonObject.getMemberStringOrEmpty("neighbourhood"),
////                metroIds = metroIds,
////                metroStations = metroStations,
////                latitude = jsonObject.get("location_lat").asDouble,
////                longitude = jsonObject.get("location_lon").asDouble,
////                rating = jsonObject.getIfHasMember("rating")?.asInt ?: 0,
////                specialties = jsonObject.getIfHasMember("specialties")
////                    ?.let {
////                        context.deserialize<List<ClinicDoctorSpecialtyNetModel>>(it, clinicDoctorSpecialtyListType)
////                            .apply { forEach { it.clinicId = id } }
////                    }
////                    ?: emptyList()
////            )
////        }
////    }
//}