const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const createExpoWebpackConfigAsync = require("@expo/webpack-config");

module.exports = {
  entry: "./src/index.jsx",
  output: {
    path: path.join(__dirname, "/dist"),
    filename: "bundle.js",
    assetModuleFilename: "[name][ext]", // (2)
  },
  resolve: {
    extensions: [".jsx", ".js"],
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules(?!\/quill-image-drop-module|quill-image-resize-module)/,
        loader: 'babel-loader',
      },
      {
        test: /\.jsx?$/,
        use: ["babel-loader"],
        exclude: /node_modules/,
      },
      {
        test: /\.css$/,
        use: ["style-loader", "css-loader"],
      },
      {
        test: /\.(PNG|JPG|JPEG|jpeg|png|jpg|ttf|eot|svg|woff(2)?)(\S+)?$/, // (1)
        type: "asset/resource",
      },
    ],
  },
  async function(env, argv) {
    const config = await createExpoWebpackConfigAsync(
      {
        ...env,
        babel: {
          dangerouslyAddModulePathsToTranspile: ["@ui-kitten/components"],
        },
      },
      argv
    );
    return config;
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: "./public/index.html",
    }),
    new webpack.ProvidePlugin({
      'window.Quill': 'quill/dist/quill.js',
      'Quill': 'quill/dist/quill.js',
    }),
  ],
};

const path = require("path");

module.exports = {
  entry: "./path/to/my/entry/file.js",
  output: {
    path: path.resolve(__dirname, "dist"),
    filename: "my-first-webpack.bundle.js",
  },
};
