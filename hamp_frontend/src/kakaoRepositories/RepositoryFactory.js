import KeywordRepository from './keywordRepository'

const repositories = {
  keyword: KeywordRepository
}

export const KakaoRepositoryFactory = {
  get: name => repositories[name]
};