/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx,vue}",
  ],
    theme: {
    extend: {},
  },
  plugins: [],
  important:true,
  prefix: 'tw-',
  corePlugins:{
    preflight: false,
  }
}
