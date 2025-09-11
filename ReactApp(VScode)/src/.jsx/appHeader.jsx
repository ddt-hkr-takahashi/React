// src/.jsx/appHeader.jsx
import React from "react";
import { Link } from "react-router-dom";

/**
 * アプリケーションのヘッダーコンポーネント
 * ナビゲーションリンクを提供する
 */
const AppHeader = () => {
  return (
    <header className="app-header">
      <nav className="main-nav">
        <ul>
          <li>
            <Link to="/">メインメニュー</Link>
          </li>
          <li>
            <Link to="/sales-info">販売情報管理</Link>
          </li>
          <li>
            <Link to="/sales-history">販売履歴管理</Link>
          </li>
          <li>
            <Link to="/owner-info">元所有者管理</Link>
          </li>
          <li>
            <Link to="/branch-info">店舗情報管理</Link>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default AppHeader;