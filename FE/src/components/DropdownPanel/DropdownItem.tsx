import { useState } from "react";
import { useParams } from "react-router-dom";
import { styled } from "styled-components";

type Props = {
  imgSource?: string;
  color?: string;
  itemName: string;
  criteria?: string;
  value?: string;
  checkbox?: boolean;
  onClick(): void;
};

export default function DropdownItem({
  imgSource = "",
  color = "",
  itemName,
  criteria,
  value,
  checkbox = true,
  onClick,
}: Props) {
  const { filter } = useParams();
  const [isSelect] = useState<boolean>(
    criteria ? criteria === value : filter === value,
  );
  return (
    <Container onClick={onClick}>
      <Info>
        {color && <ColorIcon $color={color}></ColorIcon>}
        {imgSource && <ImgIcon src={imgSource} />}
        <Label $isSelect={isSelect}>{itemName}</Label>
      </Info>
      {checkbox && <Checkbox type={"checkbox"} checked={isSelect}></Checkbox>}
    </Container>
  );
}

const Container = styled.button`
  padding: 10px 16px;
  width: 100%;
  z-index: 1000;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-top: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
`;

const Info = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

const ColorIcon = styled.div<{ $color: string }>`
  width: 20px;
  height: 20px;
  border-radius: ${({ theme }) => theme.radius.half};
  background-color: ${({ $color }) => $color};
  border: ${({ $color, theme }) =>
    `${theme.border.default} ${
      $color === "#FEFEFE" ? theme.colorSystem.neutral.text.weak : "none"
    }`};
`;

const ImgIcon = styled.img`
  width: 20px;
  height: 20px;
  border-radius: ${({ theme }) => theme.radius.half};
`;

const Label = styled.span<{ $isSelect: boolean }>`
  font: ${({ $isSelect, theme }) =>
    $isSelect ? theme.font.selectedBold16 : theme.font.availableMedium16};
  color: ${({ $isSelect, theme }) =>
    $isSelect
      ? theme.colorSystem.neutral.text.strong
      : theme.colorSystem.neutral.text.weak};
`;

const Checkbox = styled.input`
  cursor: pointer;
  appearance: none;
  width: 16px;
  height: 16px;
  background-image: url("/icons/checkOffCircle.svg");
  &:checked {
    border-color: transparent;
    background-image: url("/icons/checkOnCircle.svg");
    background-size: 100% 100%;
    background-repeat: no-repeat;
  }
`;
