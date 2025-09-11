import React from 'react';
import { Link } from 'react-router-dom';

const MainMenu = () => {
  return (
    <div className="main-menu-container">
      <h1>中古車販売管理システム</h1>
      <ul>
        <li><Link to="/sales-info">販売情報管理</Link></li>
        <li><Link to="/sales-history">販売履歴管理</Link></li>
        <li><Link to="/owner-info">元所有者管理</Link></li>
        <li><Link to="/branch-info">店舗情報管理</Link></li>
      </ul>
    </div>
  );
};

export default MainMenu;