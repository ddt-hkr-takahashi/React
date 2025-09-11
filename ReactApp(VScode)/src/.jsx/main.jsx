import React from 'react';
import ReactDOM from 'react-dom/client';
import Modal from 'react-modal';
import App from './App.jsx';

// react-modalのroot要素を設定
Modal.setAppElement('#root');

/**
 * アプリケーションのエントリーポイント
 * アプリケーションをDOMにレンダリングする
 */
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
);