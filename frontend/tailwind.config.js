/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: "class",
  content: ["./src/**/*.{html,ts}"],
  theme: {
    fontFamily: {
      sans: [
        "Inter",
        "-apple-system",
        "Segoe UI",
        "Roboto",
        "Helvetica Neue",
        "sans-serif",
      ],
    },
    extend: {
      colors: {
        "darker-blue": "#161A1D",
      },
    },
  },
  plugins: [],
};
