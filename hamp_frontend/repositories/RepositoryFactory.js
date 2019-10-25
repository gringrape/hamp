import MeetingsRepository from './meetingsRepository';
import SessionRepository from './sessionRepository';
import UsersRepository from './usersRepository';
import TopicsRepository from './topicRepository';
import ApplyingUsersRepository from './applyingUsersRepository'

const repositories = {
  meetings: MeetingsRepository,
  session: SessionRepository,
  users: UsersRepository,
  topics: TopicsRepository,
  applyingUsers: ApplyingUsersRepository
}

export const RepositoryFactory = {
  get: name => repositories[name]
};