import torch
import random
import numpy as np
from replay_buffer import ReplayBuffer
from actor import QNetwork

class DQNTrainer:
    def __init__(self, env, state_size, action_size, buffer_size=100000, batch_size=64, lr=0.001, gamma=0.99, epsilon=1.0, epsilon_decay=0.995, epsilon_min=0.01):
        self.env = env
        self.state_size = state_size
        self.action_size = action_size
        self.gamma = gamma
        self.epsilon = epsilon
        self.epsilon_decay = epsilon_decay
        self.epsilon_min = epsilon_min
        self.replay_buffer = ReplayBuffer(buffer_size, batch_size)

        # Q-network and target network
        self.q_network = QNetwork(state_size, action_size)
        self.target_network = QNetwork(state_size, action_size)
        self.update_target_network()

        self.optimizer = torch.optim.Adam(self.q_network.parameters(), lr=lr)

    def update_target_network(self):
        self.target_network.load_state_dict(self.q_network.state_dict())

    def train(self, n_episodes, target_update_freq):
        for episode in range(n_episodes):
            state, _ = self.env.reset()
            done = False
            total_reward = 0

            while not done:
                action = self.act(state)
                next_state, reward, done, truncated, _ = self.env.step(action)
                total_reward += reward
                self.replay_buffer.add((state, action, reward, next_state, done))
                state = next_state
                self.learn()

                if done or truncated:
                    break

            self.epsilon = max(self.epsilon_min, self.epsilon * self.epsilon_decay)

            if episode % target_update_freq == 0:
                self.update_target_network()

            print(f"Episode {episode}, Total Reward: {total_reward}")

    def act(self, state):
        if np.random.rand() <= self.epsilon:
            return random.randrange(self.action_size)
        else:
            state_tensor = torch.FloatTensor(state).unsqueeze(0)
            with torch.no_grad():
                q_values = self.q_network(state_tensor)
            return torch.argmax(q_values).item()

    def learn(self):
        if len(self.replay_buffer) < self.replay_buffer.batch_size:
            return

        experiences = self.replay_buffer.sample()

        for state, action, reward, next_state, done in experiences:
            state = torch.FloatTensor(state)
            next_state = torch.FloatTensor(next_state)
            reward = torch.FloatTensor([reward])
            done = torch.FloatTensor([done])

            # Calculate target Q-value
            with torch.no_grad():
                target_q_value = reward + (1 - done) * self.gamma * torch.max(self.target_network(next_state))

            # Get current Q-value
            q_value = self.q_network(state)[action]

            # Compute loss
            loss = torch.nn.functional.mse_loss(q_value, target_q_value)

            # Optimize
            self.optimizer.zero_grad()
            loss.backward()
            self.optimizer.step()
