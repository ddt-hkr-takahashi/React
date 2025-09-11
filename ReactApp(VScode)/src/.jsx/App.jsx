// /src/.jsx/App.jsx
import React from 'react';
import { BrowserRouter as Router, Route, Routes, useLocation } from 'react-router-dom';
import AppHeader from './appHeader.jsx';
import MainMenu from './MainMenu.jsx';
import SalesInformationMenu from '/src/Cars/SalesInformationMenu.jsx';
import OwnersInformationMenu from '/src/Owner/OwnersInformationMenu.jsx';
import BranchesInformationMenu from '/src/Store/BranchesInformationMenu.jsx';
import SalesHistoryMenu from '/src/History/SalesHistoryMenu.jsx';
import "/src/.css/CommonStyles.css";

/**
 * ヘッダーとルーティングを管理するコンポーネント
 * パスが'/'以外の場合にヘッダーを表示する
 */
function HeaderAndRoutes() {
  // 現在のロケーションを取得
  const location = useLocation();
  // パスがメインメニューではない場合にヘッダーを表示
  const showHeader = location.pathname !== '/';

  return (
    <>
      {showHeader && <AppHeader />}
      <Routes>
        <Route path="/" element={<MainMenu />} />
        <Route path="/sales-info" element={<SalesInformationMenu />} />
        <Route path="/owner-info" element={<OwnersInformationMenu />} />
        <Route path="/branch-info" element={<BranchesInformationMenu />} />
        <Route path="/sales-history" element={<SalesHistoryMenu />} />
      </Routes>
    </>
  );
}

/**
 * アプリケーションのメインコンポーネント
 * ルーターをラップして、ルーティング機能を有効にする
 */
function App() {
  return (
    <Router>
      <HeaderAndRoutes />
    </Router>
  );
}

export default App;