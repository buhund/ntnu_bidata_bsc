import { onMounted } from 'vue';

export function useTheme() {
  const setTheme = (theme: string) => {
    document.body.className = theme;
    localStorage.setItem('theme', theme);
  };

  const toggleTheme = () => {
    const currentTheme = document.body.classList.contains('light-mode') ? 'light' : 'dark';
    setTheme(currentTheme === 'dark' ? 'light-mode' : 'dark-mode');
  };

  onMounted(() => {
    const savedTheme = localStorage.getItem('theme') || 'dark-mode';
    document.body.className = savedTheme;
  });

  return { toggleTheme };
}
