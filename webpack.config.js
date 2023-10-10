const TerserPlugin = require("terser-webpack-plugin");
const { SourceMapDevToolPlugin } = require("webpack");
const path = require("path");

module.exports = {
    entry: {
        app: path.resolve(__dirname, "src/main/resources/src/js/index.js"),
    },
    output: {
        filename: "[name].bundle.js",
        path: path.resolve(__dirname, "src/main/resources/static/js/"),
    },
    resolve: {
        extensions: ["", ".js", ".jsx", ".css"],
    },
    plugins: [
        new TerserPlugin(),
        new SourceMapDevToolPlugin({
            filename: "[file].map",
        }),
    ],
    optimization: {
        minimizer: [new TerserPlugin()],
    },
};
