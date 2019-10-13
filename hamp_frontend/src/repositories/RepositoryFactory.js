import MeetingsRepository from './meetingsRepository';
import SessionRepository from './sessionRepository';
import UsersRepository from './usersRepository';

const repositories = {
  meetings: MeetingsRepository,
  session: SessionRepository,
  users: UsersRepository
}

export const RepositoryFactory = {
  get: name => repositories[name]
};