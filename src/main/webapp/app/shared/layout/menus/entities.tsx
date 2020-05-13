import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/restaurant">
      <Translate contentKey="global.menu.entities.restaurant" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/categories">
      <Translate contentKey="global.menu.entities.categories" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/articles-option-groups">
      <Translate contentKey="global.menu.entities.articlesOptionGroups" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/articles">
      <Translate contentKey="global.menu.entities.articles" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/articles-options">
      <Translate contentKey="global.menu.entities.articlesOptions" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/qr-code-tables">
      <Translate contentKey="global.menu.entities.qrCodeTables" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/orders">
      <Translate contentKey="global.menu.entities.orders" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
