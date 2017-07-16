const path = require('path');
const webpack = require('webpack');
const argv = require('yargs').argv;

const HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');

const isDebug = !(argv.p || false);

const saasLoader = isDebug ? 'style-loader!css-loader!sass-loader' :
    ExtractTextPlugin.extract({fallback: 'style-loader', use: 'css-loader!sass-loader'});
const cssLoader = isDebug ? 'style-loader!css-loader' :
    ExtractTextPlugin.extract({fallback: 'style-loader', use: 'css-loader'});

const config = {
    entry: [
        // "font-awesome-webpack!./src/theme/fonts.config.js",
        "./src/client.js"
    ],

    output: {
        path: path.join(__dirname, './dist'),
        filename: '[name].[hash].js'
    },

    module: {
        loaders: [
            {
                test: /\.(scss)$/,
                loader: saasLoader
            }, {
                test: /\.css$/,
                loader: cssLoader
            }, {
                test: /\.(js|jsx)$/,
                exclude: /node_modules/,
                use: [
                    {
                        loader: 'babel-loader',
                        options: {
                            presets: [
                                ['es2015', {modules: false}],
                                'stage-0',
                                'react'
                            ],
                            plugins: [
                                'transform-decorators-legacy',
                                'react-hot-loader/babel',
                            ]
                        }
                    },
                ],
            },
            {
                test: /\.json$/,
                loader: 'json-loader'
            },
            {
                test: /\.woff($|\?)|\.woff2($|\?)|\.ttf($|\?)|\.eot($|\?)|\.svg($|\?)/,
                loader: 'url-loader'
            },
            {
                test: /\.(jpe?g|png|gif|svg)$/i,
                use: [
                    'url-loader?limit=10000',
                    {
                        loader: 'img-loader',
                        options: {
                            enabled: 'developer'
                        }
                    }
                ]
            }
        ]
    },

    resolve: {
        extensions: ['.js'],
        alias: {
            cfg: path.resolve(__dirname, './config.json')
        },
    },

    plugins: [
        new webpack.optimize.ModuleConcatenationPlugin(),

        // OccurenceOrderPlugin: Assign the module and chunk ids by occurrence count. : https://webpack.github.io/docs/list-of-plugins.html#occurenceorderplugin
        new webpack.optimize.OccurrenceOrderPlugin(),

        new ExtractTextPlugin('[name].[hash].css'),

        new HtmlWebpackPlugin({
            filename: 'index.html',
            template: './src/index.html',
            inject: 'body',
            // chunksSortMode: 'dependency',
            minify: false
        }),

        new webpack.DefinePlugin({
            __DEBUG__: isDebug,
            'process.env': {
                NODE_ENV: process.env.NODE_ENV ? JSON.stringify(process.env.NODE_ENV) : JSON.stringify('development')
            }
        }),

        new webpack.optimize.CommonsChunkPlugin({
            name: 'vendor',
            filename: 'vendor.[hash].js',
            minChunks(module) {
                const context = module.context;
                return context && context.indexOf('node_modules') >= 0;
            },
        }),
    ],

    devtool: isDebug ? 'eval' : false,
};

module.exports = config;
