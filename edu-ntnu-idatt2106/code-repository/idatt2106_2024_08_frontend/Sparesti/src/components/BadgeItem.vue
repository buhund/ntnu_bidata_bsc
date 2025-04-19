<template>
    <div class="item">
      <i>
        <slot name="icon"></slot>
      </i>
      <div class="details">
        <h3>
          <slot name="heading"></slot>
        </h3>
      </div>
    </div>
  </template>
  
  <style scoped>
  .item {
    margin-top: 0;
    display: flex;
    position: relative;
    align-items: center;
  }
  
  .details {
    flex: 1;
    margin-left: 1rem;
    align-self: top;
  }
  
  i {
    display: flex;
    place-items: center;
    width: 32px;
    height: 32px;
  }
  
  h3 {
    font-size: 1.2rem;
    font-weight: 400;
    align-content: center;
  }
  
  @media (min-width: 1024px) {
    .item {
      margin-top: 0;
    }
  
    i {
      top: calc(50% - 25px);
      left: -26px;
      position: absolute;
      border-radius: 8px;
      width: 50px;
      height: 50px;
    }
  
    .item:before {
      content: ' ';
      position: absolute;
      left: 0;
      bottom: calc(50% + 25px);
      height: calc(50% - 25px);
    }
  
    .item:after {
      content: ' ';
      position: absolute;
      left: 0;
      top: calc(50% + 25px);
      height: calc(50% - 25px);
    }
  
    .item:first-of-type:before {
      display: none;
    }
  
    .item:last-of-type:after {
      display: none;
    }
  }
  </style>
  