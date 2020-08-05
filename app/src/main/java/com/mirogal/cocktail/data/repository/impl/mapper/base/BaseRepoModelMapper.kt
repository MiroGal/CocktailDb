package com.mirogal.cocktail.data.repository.impl.mapper.base

abstract class BaseRepoModelMapper<RepoModel, DbModel, NetModel> {

    open fun mapDbToRepo(db: DbModel): RepoModel {
        throw NotImplementedError(message = "provide mapping for model ${db.hashCode()}")
    }

    open fun mapRepoToDb(repo: RepoModel): DbModel {
        throw NotImplementedError(message = "provide mapping for model ${repo.hashCode()}")
    }

    open fun mapNetToRepo(net: NetModel): RepoModel {
        throw NotImplementedError(message = "provide mapping for model ${net.hashCode()}")
    }

    open fun mapRepoToNet(repo: RepoModel): NetModel {
        throw NotImplementedError(message = "provide mapping for model ${repo.hashCode()}")
    }

    open fun mapNetToDb(net: NetModel): DbModel {
        throw NotImplementedError(message = "provide mapping for model ${net.hashCode()}")
    }

    open fun mapDbToRepo(db: List<DbModel>): List<RepoModel> = db.map(::mapDbToRepo)
    open fun mapRepoToDb(repo: List<RepoModel>): List<DbModel> = repo.map(::mapRepoToDb)
    open fun mapNetToRepo(net: List<NetModel>): List<RepoModel> = net.map(::mapNetToRepo)

}