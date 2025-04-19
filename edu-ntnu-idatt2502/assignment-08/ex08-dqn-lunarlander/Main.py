import gymnasium as gym
from trainer import DQNTrainer

def main():
    env = gym.make("LunarLander-v3")
    state_size = env.observation_space.shape[0]
    action_size = env.action_space.n

    trainer = DQNTrainer(env, state_size, action_size)
    trainer.train(n_episodes=1000, target_update_freq=10)

if __name__ == "__main__":
    main()
