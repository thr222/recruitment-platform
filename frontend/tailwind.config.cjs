module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        wellfound: {
          black: '#000000',
          white: '#FFFFFF',
          gray: '#F5F5F5',
          red: '#FF4D4D',
        }
      },
      fontFamily: {
        wellfound: ['"Inter"', 'sans-serif'],
      }
    },
  },
  plugins: [],
}
